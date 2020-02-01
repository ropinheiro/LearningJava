package com.packt.springboot.blogmania;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BeanData {
    private final String Bean;
    private final String BeanClass;
}