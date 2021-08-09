package com.ycitus.files;

import java.io.IOException;

public class ApplicationConfig_File extends ConfigFile {

	public ApplicationConfig_File(String path, String fineName,
								  @SuppressWarnings("rawtypes") Class configDataClass)
			throws IllegalArgumentException, IllegalAccessException,
			IOException {
		super(path, fineName, configDataClass);
	}

	public ApplicationConfig_Data getSpecificDataInstance() {
		return (ApplicationConfig_Data) super.getConfigDataClassInstance();
	}

}
