package com.valeo.nio.iterate_directory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.valeo.common.util.ClassDesign;
import com.valeo.common.util.IntrospectClass;
import com.valeo.common.util.SpreadsheetHelper;
import com.valeo.nio.iterate_directory.runner.CLIConfig;

public final class Listing {

	private final CLIConfig config;
	private final ConsoleView view;
	private String rootPath;

	public void getFileInfo() {

		IntrospectClass introspect = null;
		List<ClassDesign> classDesignList = new ArrayList<ClassDesign>();

		System.out.println(rootPath);
		for (int i = 0; i < view.listOfFilePath.size(); i++) {
			ClassDesign clsDesignObj = new ClassDesign();
			String absoluteFile = view.listOfFilePath.get(i).toString();
			
			if(!absoluteFile.endsWith(".class"))
				continue;
			
			String fullQFileName = absoluteFile
					.substring(rootPath.length() + 1).replace('\\', '.')
					.replace(".class", "");
			System.out.println(fullQFileName);

			try {
				introspect = new IntrospectClass("file:///"
						+ rootPath.replace('\\', '/'), fullQFileName);
				//clsDesignObj.setClsName(fullQFileName);
				clsDesignObj.setClsName(introspect.getClassName());
				clsDesignObj.setPackageName(introspect.getPackageName());
				clsDesignObj.setMethods(introspect.getMethods());
				clsDesignObj.setConstructors(introspect.getConstructors());
				clsDesignObj.setFields(introspect.getFields());
				classDesignList.add(clsDesignObj);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			new SpreadsheetHelper().writeExcel(classDesignList,
					rootPath.replace('\\', '/') + "/ClassDesign.xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Listing(final CLIConfig config) {
		this.config = config;
		this.view = new ConsoleView(config);
	}

	public void list() {
		final Path root = Paths.get(this.config.getRoot().orElseThrow(
				RuntimeException::new));

		if (Files.isDirectory(root)) {
			final Directory rootDir = new Directory(root);

			list(rootDir, this.config.getFilter());
			this.view.render(rootDir);
			this.rootPath = rootDir.getPath().toString();

		} else {
			throw new RuntimeException("Root needs to be a directory");
		}

		getFileInfo();
	}

	private void list(final Directory directory, final Optional<String> filter) {
		assert !Objects.isNull(directory) && !Objects.isNull(filter);

		DirectoryStream<Path> stream = null;
		try {
			if (filter.isPresent()) {
				stream = Files.newDirectoryStream(directory.getPath(),
						createFilter(filter.get()));
			} else {
				stream = Files.newDirectoryStream(directory.getPath());
			}

			for (Path path : stream) {
				if (Files.isDirectory(path)) {
					final Directory child = new Directory(path);
					directory.addDirectory(child);

					list(child, filter);
				} else {
					directory.addFile(new File(path));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (!Objects.isNull(stream)) {
				try {
					stream.close();
				} catch (IOException e) {
					throw new RuntimeException(
							"unable to close stream while listing directories",
							e);
				}
			}
		}
	}

	private Filter<Path> createFilter(final String pattern) {

		return new Filter<Path>() {

			@Override
			public boolean accept(final Path entry) throws IOException {
				return Files.isDirectory(entry)
						|| entry.toFile().getName().contains(pattern);
			}
		};
	}
}
