package src.model;

import src.dao.OrdenesDao;
import src.dao.ProductosDao;
import src.dao.ServidoDao;
import src.model.abstractmodel.AbstractServido;

public class Servido extends AbstractServido implements IPersistable {

    private Producto producto;
    private Orden orden;

    public Servido(int id, int idOrden, int idProducto) {
        super(id, idOrden, idProducto);
        refreshProducto();
    }

    public Servido(int idOrden, int idProducto) {
        super(idOrden, idProducto);
        refreshProducto();
    }

    public Servido(int idOrden, Producto producto) {
        super(idOrden, producto.getId());
        this.producto = producto;
    }

    @Override
    public void setIdProducto(int idProducto) {
        super.setIdProducto(idProducto);
        refreshProducto();
    }

    @Override
    public void setIdOrden(int idOrden) {
        super.setIdOrden(idOrden);
        //refreshOrden();
    }

    public Producto getProducto() {
        return producto;
    }

    public Orden getOrden() {
        return orden;
    }

    @Override
    public int updateOnDb() {
        return ServidoDao.getInstance().update(this);
    }

    @Override
    public int refreshFromDb() {
        return ServidoDao.getInstance().updateDao(this);
    }

    private void refreshProducto() {
        producto = ProductosDao.getInstance().get(idProducto);
    }

    private void refreshOrden() {
        orden = OrdenesDao.getInstance().get(idOrden);
    }

}
