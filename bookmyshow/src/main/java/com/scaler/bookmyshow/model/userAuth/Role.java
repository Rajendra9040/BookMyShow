package com.scaler.bookmyshow.model.userAuth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scaler.bookmyshow.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@JsonDeserialize
public class Role extends BaseModel {
    @Column(name = "name")
    private String name;
}
