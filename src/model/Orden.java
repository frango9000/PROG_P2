package src.model;

import src.dao.OrdenesDao;
import src.dao.ServidoDao;
import src.model.abstractmodel.AbstractOrden;

import java.time.LocalDateTime;

public class Orden extends AbstractOrden implements IPersistable {

    public Orden() {
        super();
    }

    public Orden(LocalDateTime apertura, LocalDateTime cierre, float total) {
        super(apertura, cierre, total);
    }

    public Orden(int id, String dbOpen, String dbClose, float total) {
        super(id, dbOpen, dbClose, total);
        updateServidos();
    }

    @Override
    public int updateOnDb() {
        return OrdenesDao.getInstance().update(this);
    }

    @Override
    public int refreshFromDb() {
        return OrdenesDao.getInstance().updateDao(this);
    }

    private void updateServidos() {
        servidos = ServidoDao.getInstance().getByIdOrden(this.id);
    }
}
