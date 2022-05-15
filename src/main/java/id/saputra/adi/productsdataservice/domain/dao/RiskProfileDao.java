package id.saputra.adi.productsdataservice.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RISK_PROFILE")
public class RiskProfileDao implements Serializable {
    private static final long serialVersionUID = 174393103434885095L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    private boolean isDeleted;
}
