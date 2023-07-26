package com.hms.entities;

import com.hms.enums.AdmissionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@RequiredArgsConstructor
@Setter
@Getter
@Entity
public class Patient extends User {

    @Column
    private String treatmentName;

}
