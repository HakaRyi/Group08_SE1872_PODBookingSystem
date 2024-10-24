package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.Image.CreateImageRequest;
import com.example.POD_BookingSystem.DTO.Request.Image.UpdateImageRequest;
import com.example.POD_BookingSystem.DTO.Response.ImageResponse;
import com.example.POD_BookingSystem.Entity.Image;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public Image toImage(CreateImageRequest request) {
        if ( request == null ) {
            return null;
        }

        Image.ImageBuilder image = Image.builder();

        image.image( request.getImage() );
        image.alt_text( request.getAlt_text() );

        return image.build();
    }

    @Override
    public ImageResponse toImageResponse(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageResponse.ImageResponseBuilder imageResponse = ImageResponse.builder();

        imageResponse.image_id( image.getImage_id() );
        imageResponse.image( image.getImage() );
        imageResponse.alt_text( image.getAlt_text() );

        return imageResponse.build();
    }

    @Override
    public void updateImage(Image image, UpdateImageRequest request) {
        if ( request == null ) {
            return;
        }

        image.setImage( request.getImage() );
        image.setAlt_text( request.getAlt_text() );
    }
}
