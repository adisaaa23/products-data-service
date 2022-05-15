package id.saputra.adi.productsdataservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRiskDto implements Serializable {
    private static final long serialVersionUID = 4644976563713374501L;
    private String code;
    private String name;
    private String riskProfile;
    private BigInteger balance;
    private BigInteger minAmount;
    private BigInteger maxAmount;
    private BigInteger multipleAmount;
    private String riskProfileName;
}
