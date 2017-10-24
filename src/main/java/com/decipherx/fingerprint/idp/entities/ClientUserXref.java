//package com.decipherx.fingerprint.idp.entities;
//
//import javax.persistence.*;
//import java.util.Collection;
//import java.util.List;
//
//@Entity
////@Table(name = "client_user_xref")
//public class ClientUserXref {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToMany(mappedBy = "id")
//    private Collection<Client> clientList;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Collection<Client> getClientList() {
//        return clientList;
//    }
//
//    public void setClientList(Collection<Client> clientList) {
//        this.clientList = clientList;
//    }
//}
