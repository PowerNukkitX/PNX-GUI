package cn.powernukkitx.gui.share;

import cn.powernukkitx.gui.util.OSUtils;

import java.io.File;
import java.util.List;

public interface GUIConstant {
    String version = "0.0.1";
    List<String> authors = List.of("超神的冰凉");
    File userDir = new File(System.getProperty("user.dir"));
    File programDir = new File(OSUtils.getProgramDir());
    File cacheDir = new File(programDir, "runtime_data/cache");
}
