/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvb.imaginapainting.data;

import com.dvb.imaginapainting.entities.BuyerCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel Bart
 */
@Repository
public interface BuyerCartRepository extends JpaRepository<BuyerCart, Integer> {
    
}
