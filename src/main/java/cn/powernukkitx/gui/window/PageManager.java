package cn.powernukkitx.gui.window;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PageManager {
    private final List<CefIndexPage> pageList;
    private int selectedIndex = -1;

    public PageManager() {
        this.pageList = new ArrayList<>();
    }

    public int size() {
        return this.pageList.size();
    }

    public void addPage(CefIndexPage page) {
        this.pageList.add(page);
        if (this.pageList.size() == 1) {
            selectPage(0);
        }
    }

    public CefIndexPage closePage(int index) {
        var page = removePage(index);
        page.close();
        if (selectedIndex <= index) {
            selectPage(index - 1);
        }
        return page;
    }

    public CefIndexPage selectPage(int index) {
        this.selectedIndex = index;
        return this.pageList.get(index);
    }

    public CefIndexPage getSelectedPage() {
        return getPage(selectedIndex);
    }

    public CefIndexPage getPage(int index) {
        return this.pageList.get(index);
    }

    public CefIndexPage removePage(int index) {
        return this.pageList.remove(index);
    }

    @Contract(pure = true)
    public @Nullable CefIndexPage getPageById(int id) {
        for (var page : this.pageList) {
            if (page.id() == id) {
                return page;
            }
        }
        return null;
    }

    public void removePage(CefIndexPage page) {
        this.pageList.remove(page);
    }

    public void clear() {
        this.pageList.clear();
    }

    @Contract(pure = true)
    public @NotNull @UnmodifiableView List<CefIndexPage> getPageList() {
        return Collections.unmodifiableList(this.pageList);
    }
}
