package com.project.antiguaburguers.dto;

import org.springframework.http.ResponseCookie;

public record ResponseAuthDTO<T>(ResponseCookie accessCookie, ResponseCookie refreshCookie, T response) {
}
