package id.saputra.adi.productsdataservice.services;

public interface CrudBase {

    Object create(Object productDto);
    Object update(Object productDto);
    Object delete(Object productDto);
    Object showAll();
    Object showDetail(String username);

}
