package id.saputra.adi.productsdataservice.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PRODUCT")
public class ProductDao implements Serializable {

    private static final long serialVersionUID = -7080418023821763207L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String name;
    private String riskProfile;
    private BigInteger balance;
    private BigInteger minAmount;
    private BigInteger maxAmount;
    private BigInteger multipleAmount;
    private boolean isDeleted;
}
