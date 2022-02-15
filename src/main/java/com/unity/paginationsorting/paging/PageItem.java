package com.unity.paginationsorting.paging;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageItem {

    private PageItemType pageItemType;
    private int index;
    private boolean active;

}
