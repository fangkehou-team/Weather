package org.eu.fangkehou.weather.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskBean {
    Integer id;
    String name;
    String date;
    String time;
}
