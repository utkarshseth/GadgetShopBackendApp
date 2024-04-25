package com.personal.gadgetstore.helper;

import com.personal.gadgetstore.dto.PageableResponse;
import com.personal.gadgetstore.dto.UserDto;
import com.personal.gadgetstore.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PageableHelper {

    @Autowired
    private static ModelMapper modelMapper;

    public static <U, V> PageableResponse<V> createPageableResponse(Page<U> page, Class<V> type) {
        List<U> entity = page.getContent();
        List<V> list = entity.stream()
                .map(object -> new ModelMapper().map(object, type))
                .collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(list);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }
}
