package passwordmanager.encoded;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import passwordmanager.manager.Logger;

/**
 * Data structure with encrypted records implementation
 * 
 * @see IRawData
 * @author Doomaykaka MIT License
 * @since 2023-12-14
 */
public class CheckedRawData implements IRawData {
	/**
	 * Class version number for checks when desirializing and serializing class
	 * objects
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * List of encrypted records
	 */
	private List<String> data;
	/**
	 * Name of an encrypted data structure
	 */
	private String name;
	/**
	 * The path along which the structure will be saved
	 */
	private String pathToSaveFile;

	/**
	 * Constructor that initializes a list of encrypted entries, name structure and
	 * the path in which it can be stored
	 */
	public CheckedRawData() {
		data = new ArrayList<String>();
		name = "default";
		pathToSaveFile = System.getProperty("user.dir");
		generateSaveFilePath();
	}

	/**
	 * Method for getting a list of encrypted records from a structure
	 * 
	 * @return list of encrypted entries
	 */
	@Override
	public List<String> getData() {
		Logger.addLog("RawData", "getting raw data");
		return this.data;
	}

	/**
	 * Method for changing the list of encrypted entries in a structure
	 * 
	 * @param newData
	 *            list of encrypted entries
	 */
	@Override
	public void setData(List<String> newData) {
		Logger.addLog("RawData", "raw data content changing");
		data = newData;
	}

	/**
	 * Method for checking the correctness of the structure with encrypted records
	 * 
	 * @return whether the data structure is correct or not
	 */
	@Override
	public boolean checkData() {
		Logger.addLog("RawData", "raw data checking");
		if ((data != null) && (data.size() > 0)) {
			boolean containsNull = false;

			for (String row : data) {
				if (row == null) {
					containsNull = true;
					break;
				}
			}

			return !containsNull;
		}

		return data != null;
	}

	/**
	 * Method for getting the name of an encrypted data structure
	 * 
	 * @return data structure name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Method for setting the name of an encrypted data structure
	 * 
	 * @param newName
	 *            new data structure name
	 */
	@Override
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * Method for saving structure
	 */
	@Override
	public void save() {
		generateSaveFilePath();

		try {
			FileWriter writer = new FileWriter(getPathToSaveFile());

			for (String line : getData()) {
				if (checkData()) {
					writer.write(line);
					writer.write("\n");
				}
			}

			writer.close();
		} catch (IOException e) {
			Logger.addLog("RawData", "saving error");
		}
	}

	/**
	 * Method for restoring structure
	 */
	@Override
	public void load() {
		generateSaveFilePath();

		try {
			List<String> data = new ArrayList<String>();

			Scanner scanner = new Scanner(new File(getPathToSaveFile()));
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
			scanner.close();

			setData(data);
		} catch (IOException e) {
			Logger.addLog("RawData", "loading error");
		}
	}

	/**
	 * Method for cloning a structure object with encrypted records
	 * 
	 * @return cloned structure
	 */
	public CheckedRawData clone() {
		CheckedRawData clone = new CheckedRawData();
		clone.data = new ArrayList<String>();
		for (String row : data) {
			clone.data.add(row);
		}
		return clone;
	}

	/**
	 * Method for obtaining the path along which the structure will be saved
	 * 
	 * @return path to the saving file
	 */
	public String getPathToSaveFile() {
		return pathToSaveFile;
	}

	/**
	 * Method for changing the path where the structure will be saved
	 * 
	 * @param pathToSaveFile
	 *            the path along which the structure will be saved
	 */
	public void setPathToSaveFile(String pathToSaveFile) {
		this.pathToSaveFile = pathToSaveFile;
	}

	/**
	 * Method generating default path for saving and restoring structure
	 */
	private void generateSaveFilePath() {
		try {
			String separator = "";
			pathToSaveFile = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().toString();

			int dirSlashIdx = 0;
			dirSlashIdx = pathToSaveFile.lastIndexOf("/");
			if (dirSlashIdx != -1) {
				pathToSaveFile = pathToSaveFile.substring(0, dirSlashIdx);
				separator = "/";
			} else {
				separator = "/";
				dirSlashIdx = pathToSaveFile.lastIndexOf("\\");
				if (dirSlashIdx != -1) {
					pathToSaveFile = pathToSaveFile.substring(0, dirSlashIdx);
				} else {
					throw new URISyntaxException("checkRootPathString", "Bad path");
				}
			}

			dirSlashIdx = pathToSaveFile.indexOf(separator);
			pathToSaveFile = pathToSaveFile.substring(dirSlashIdx + 1);
			pathToSaveFile = pathToSaveFile + separator + getName() + ".dat";

		} catch (URISyntaxException e) {
			Logger.addLog("RawData", "getting root path error");
		}
	}
}
