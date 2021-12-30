package com.jetwinner.webfast.kernel;

import com.jetwinner.servlet.RequestContextPathUtil;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author xulixin
 */
public class Paginator {

    protected int itemCount;

    protected int perPageCount;

    protected int currentPage;

    protected int pageRange = 10;

    protected String baseUrl;

    protected String pageKey = "page";

    public Paginator(HttpServletRequest request, int total) {
        this(request, total, 20);
    }

    public Paginator(HttpServletRequest request, int total, int perPage) {
        this.setItemCount(total);
        this.setPerPageCount(perPage);

        int page = ServletRequestUtils.getIntParameter(request, pageKey, 0);
        int maxPage = total / perPage;
        if (maxPage < 1) {
            maxPage = 1;
        }
        this.setCurrentPage(page <= 0 ? 1 : (page > maxPage ? maxPage : page));

        this.setBaseUrl(request);
    }

    public Paginator setItemCount(int count) {
        this.itemCount = count;
        return this;
    }

    public Paginator setPerPageCount(int count) {
        this.perPageCount = count;
        return this;
    }

    public Integer getPerPageCount() {
        return this.perPageCount;
    }

    public Paginator setCurrentPage(int page) {
        this.currentPage = page;
        return this;
    }

    public Paginator setPageRange(int range) {
        this.pageRange = range;
        return this;
    }

    public void setBaseUrl(HttpServletRequest request) {
        StringBuilder template = new StringBuilder();
        template.append(RequestContextPathUtil.createBaseUrl(request));

        boolean firstPlace = true;
        Enumeration<String> paramKeys = request.getParameterNames();
        while (paramKeys.hasMoreElements()) {
            String pKey = paramKeys.nextElement();
            if (pageKey.equals(pKey)) {
                continue;
            }
            if (firstPlace) {
                template.append("?");
                firstPlace = false;
            } else {
                template.append("&");
            }
            template.append(pKey).append("=").append(request.getParameter(pKey));
        }
        template.append(firstPlace ? "?" : "&").append(pageKey).append("=").append("{page}");
        this.baseUrl = template.toString();
    }

    public String getPageUrl(int page) {
        return this.baseUrl == null ? "" :
                this.baseUrl.replaceAll("\\{page}", String.valueOf(page));
    }

    public Integer getPageRange() {
        return this.pageRange;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public Integer getFirstPage() {
        return 1;
    }

    public Integer getLastPage() {
        return this.itemCount / this.perPageCount;
    }

    public Integer getPreviousPage() {
        int diff = this.getCurrentPage() - this.getFirstPage();
        return diff > 0 ? this.getCurrentPage() - 1 : this.getFirstPage();
    }

    public Integer getNextPage() {
        int diff = this.getLastPage() - this.getCurrentPage();
        return diff > 0 ? this.getCurrentPage() + 1 : this.getLastPage();
    }

    public Integer getOffsetCount() {
        return (this.getCurrentPage() - 1) * this.perPageCount;
    }

    public Integer getItemCount() {
        return this.itemCount;
    }

    public Integer[] getPages() {
        int previousRange = (int) Math.round(this.getPageRange() / 2.0);
        int nextRange = this.getPageRange() - previousRange - 1;

        int start = this.getCurrentPage() - previousRange;
        start = start <= 0 ? 1 : start;

        int[] pages = range(start, this.getCurrentPage());

        int end = this.getCurrentPage() + nextRange;
        end = end > this.getLastPage() ? this.getLastPage() : end;

        if (this.getCurrentPage() + 1 <= end) {
            pages = mergeArray(pages, range(this.getCurrentPage() + 1, end));
        }
        return toIntegerArray(pages);
    }

    private Integer[] toIntegerArray(int[] fromArray) {
        Integer[] array = new Integer[fromArray.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = fromArray[i];
        }
        return array;
    }

    private int[] mergeArray(int[] array1, int[] array2) {
        int[] array = new int[array1.length + array2.length];
        int i;
        for (i = 0; i < array1.length; i++) {
            array[i] = array1[i];
        }
        for (int k : array2) {
            array[i++] = k;
        }
        return array;
    }

    /**
     * return array an array of elements from start to end, inclusive.
     */
    private int[] range(int start, int end) {
        int[] array = new int[end - start];
        end++;
        for (int i = 0, k = start; k < end; i++, k++) {
            array[i] = k;
        }
        return array;
    }
}