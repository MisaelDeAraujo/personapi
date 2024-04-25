package com.misael.personapi.entities;

import com.misael.personapi.entities.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="address_tb")
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 60, nullable = false)
    private String publicPlace;
    @Column(length = 8, nullable = false)
    private String zipCode;
    @Column(length = 15, nullable = false,unique = true)
    private String number;
    @Column(length = 60, nullable = false)
    private String city;
    @Column(length = 60, nullable = false)
    private String state;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

}
