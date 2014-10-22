package ru.hh.test.driver;

import java.util.Locale;

import ru.hh.test.configuration.ConfigurationException;
import ru.hh.test.driver.CustomWebDriverProvider.SupportedBrowser;
import static java.lang.Boolean.parseBoolean;

/**
 * @author e3s team
 */
public class DriverSystemPropertiesResolver {

    public static String PROP_OS_NAME = "os.name";
    public static String PROP_OS_ARCH = "os.arch";
    public static String PROP_PROJECT_BUILD_DIRECTORY = "project.build.outputDirectory";

    public static String PROP_WD_CHROME = "webdriver.chrome.driver";

    public static String OS_LINUX = "linux";
    public static String OS_WINDOWS = "windows";

    public static String ARCH_x64 = "amd64";
    public static String ARCH_x86 = "x86";

    /**
     * parameters:
     * 1 - project build outputDirectory
     * 2 - os name
     * 3 - os arch
     */
    private static String PATH_PATTERN_CHROME = "%s/driver/%s/%s/chromedriver";

    public static void applyDiverProperties() {
        String os_name = System.getProperty(PROP_OS_NAME);
        String os_arch = System.getProperty(PROP_OS_ARCH);
        String base_dir = System.getProperty(PROP_PROJECT_BUILD_DIRECTORY);
        if(os_name == null || os_name.isEmpty() || os_arch == null || os_arch.isEmpty() || base_dir == null || base_dir.isEmpty()) {
            throw new ConfigurationException(new StringBuilder().append("System properties can not be empty or null: ")
                    .append("\n '")
                    .append(PROP_OS_NAME)
                    .append("' -> [")
                    .append(os_name)
                    .append("]")
                    .append("\n '")
                    .append(PROP_OS_ARCH)
                    .append("' -> [")
                    .append(os_arch)
                    .append("]")
                    .append("\n '")
                    .append(PROP_PROJECT_BUILD_DIRECTORY)
                    .append("' -> [")
                    .append(base_dir)
                    .append("]")
                    .toString());
        }
        if(os_name.regionMatches(true, 0, OS_LINUX, 0, 5)) {
            // chrome settings
            System.setProperty(PROP_WD_CHROME, String.format(PATH_PATTERN_CHROME, base_dir, OS_LINUX, os_arch));
            // ie settings
            //... TODO
        }
        if(os_name.regionMatches(true, 0, OS_WINDOWS, 0, 7)) {
            // chrome settings
            System.setProperty(PROP_WD_CHROME, String.format(PATH_PATTERN_CHROME, base_dir, OS_WINDOWS, os_arch) + ".exe");
            // ie settings
            //... TODO
        }
    }

    public static SupportedBrowser getBrowser() {
        return SupportedBrowser.valueOf(SupportedBrowser.class, System.getProperty("browser", "firefox").toUpperCase(usingLocale()));
    }

    public static boolean isRemoteBrowser() {
        return parseBoolean(System.getProperty("remote", "false"));
    }

    public static boolean isJavascriptEnabled() {
        return parseBoolean(System.getProperty("webdriver.htmlunit.javascriptEnabled", "true"));
    }

    private static Locale usingLocale() {
        return Locale.getDefault();
    }
}
