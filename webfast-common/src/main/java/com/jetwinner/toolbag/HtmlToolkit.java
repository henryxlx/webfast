package com.jetwinner.toolbag;

import com.jetwinner.util.EasyStringUtil;

/**
 * @author xulixin
 */
public final class HtmlToolkit {

    private HtmlToolkit() {
        // reserved.
    }

    /**
     * 返回纯文本,去掉html的所有标签,并且去掉空行
     *
     * @param input
     * @return
     */
    public static String purifyHtml(String input) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        return deleteCRLF(str);
    }


    /***
     * Delete all spaces
     *
     * @param input
     * @return
     */

    public static String deleteAllCRLF(String input) {
        return input.replaceAll("((\r\n)|\n)[\\s\t ]*", "").replaceAll(
                "^((\r\n)|\n)", "");
    }

    /**
     * delete CRLF; delete empty line ;delete blank lines
     *
     * @param input
     * @return
     */
    public static String deleteCRLF(String input) {
        input = deleteCRLFOnce(input);
        return deleteCRLFOnce(input);
    }

    /***
     * delete CRLF; delete empty line ;delete blank lines
     *
     * @param input
     * @return
     */
    private static String deleteCRLFOnce(String input) {
        if (EasyStringUtil.isNotBlank(input)) {
            return input.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1")
                    .replaceAll("^((\r\n)|\n)", "");
        } else {
            return null;
        }
    }
}
