package com.uet.stockmanager.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Sale {

    @Id
    private long id;

    private Long timestamp;
    private String name;
    private int quanlity;
    private int price;

    private long productId;
    
    @ToOne(joinProperty = "productId")
    private Product product;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 595863298)
    private transient SaleDao myDao;

    @Generated(hash = 498712541)
    public Sale(long id, Long timestamp, String name, int quanlity, int price,
            long productId) {
        this.id = id;
        this.timestamp = timestamp;
        this.name = name;
        this.quanlity = quanlity;
        this.price = price;
        this.productId = productId;
    }

    @Generated(hash = 983461670)
    public Sale() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuanlity() {
        return this.quanlity;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Generated(hash = 587652864)
    private transient Long product__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 926515087)
    public Product getProduct() {
        long __key = this.productId;
        if (product__resolvedKey == null || !product__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProductDao targetDao = daoSession.getProductDao();
            Product productNew = targetDao.load(__key);
            synchronized (this) {
                product = productNew;
                product__resolvedKey = __key;
            }
        }
        return product;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1202438341)
    public void setProduct(@NotNull Product product) {
        if (product == null) {
            throw new DaoException(
                    "To-one property 'productId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.product = product;
            productId = product.getId();
            product__resolvedKey = productId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1540547603)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSaleDao() : null;
    }
}
