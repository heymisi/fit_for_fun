package hu.fitforfun.bo;


import lombok.Data;

@Data
public class ImageBO {
    private String name;
    private String type;
    private byte[] picByte;
}
