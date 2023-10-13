package com.example.entity;

import com.example.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class CheckingAccount extends Account{


}
