package org.tydd.common;

import lombok.Data;
import lombok.ToString;

/**
 * author: minkun
 * date: 2020/10/17
 * Description:
 */
@Data
@ToString
public class PageInfoDto {

    private int totalPage;

    private int curPage;
}
