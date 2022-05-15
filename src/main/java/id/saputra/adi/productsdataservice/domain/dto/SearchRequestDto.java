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
public class SearchRequestDto implements Serializable {

    private static final long serialVersionUID = -718667225012094495L;
    String riskProfile;
    BigInteger startMinAmount;
    BigInteger endMinAmount;
    BigInteger startMaxAmount;
    BigInteger endMaxAmount;
}
