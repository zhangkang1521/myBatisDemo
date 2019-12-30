package org.zk.page;

public class PageHelp {

    public static final ThreadLocal<Page> PAGE_THREAD_LOCAL = new ThreadLocal<>();

    public static void startPage(int pageNo, int pageSize) {
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        PAGE_THREAD_LOCAL.set(page);
    }
}
