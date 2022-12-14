package server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public
class EncryptionDto {
    private Integer code;
    private String username;
    private String message;
    private int key;
}