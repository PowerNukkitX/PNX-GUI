package cn.powernukkitx.gui.util;

import cn.powernukkitx.gui.share.GUIConstant;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class OSUtils {
    private OSUtils() {

    }

    public static EnumOS getOS() {
        var os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return EnumOS.WINDOWS;
        } else if (os.contains("mac") || os.contains("darwin")) {
            return EnumOS.MACOS;
        } else if (os.contains("nux")) {
            return EnumOS.LINUX;
        } else {
            return EnumOS.UNKNOWN;
        }
    }

    public static String getProgramPath() {
        var path = OSUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = java.net.URLDecoder.decode(path, StandardCharsets.UTF_8);
        if (getOS() == EnumOS.WINDOWS) {
            path = path.substring(1);
        }
        return path;
    }

    public static String getProgramDir() {
        var tmp = Path.of(getProgramPath());
        return tmp.getParent().toString();
    }

    public static String getProgramName() {
        var tmp = Path.of(getProgramPath());
        var name = tmp.getFileName().toString();
        if (name.contains(".")) {
            return StringUtils.beforeLast(name, ".");
        }
        return name;
    }

    public static Locale getWindowsLocale() {
        try {
            var cmd = """
                    reg query "hklm\\system\\controlset001\\control\\nls\\language" /v Installlanguage
                    """;
            var cmdFile = new File(GUIConstant.programDir + "/locale.cmd");
            //noinspection ResultOfMethodCallIgnored
            cmdFile.getParentFile().mkdirs();
            if (!cmdFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                cmdFile.createNewFile();
                try (var writer = new FileWriter(cmdFile)) {
                    writer.write(cmd);
                }
            }
            var process = new ProcessBuilder().command(cmdFile.getAbsolutePath())
                    .redirectErrorStream(true).start();
            process.waitFor(2500, TimeUnit.MILLISECONDS);
            try(var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                var s = "";
                while ((s = reader.readLine()) != null) {
                    if (s.contains("0804 ")) {
                        return Locale.forLanguageTag("zh-cn");
                    } else if (s.contains("0409 ")) {
                        return Locale.forLanguageTag("en-us");
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            //ignore
        }
        return null;
    }
}
