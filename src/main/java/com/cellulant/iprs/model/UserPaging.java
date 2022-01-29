package com.cellulant.iprs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPaging {
    int draw;
    long recordsTotal;
    long recordsFiltered;
    List<User> data;
}
