/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvb.imaginapainting.service;

import com.dvb.imaginapainting.data.SellerRepository;
import com.dvb.imaginapainting.entities.Seller;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Daniel Bart
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public Seller findById(int sellerId) {
        return sellerRepository.findById(sellerId).orElse(null);
    }

    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public void deleteById(int sellerId) {
        sellerRepository.deleteById(sellerId);
    }

    @Override
    public long count() {
        return sellerRepository.count();
    }

    @Override
    public boolean existsById(int sellerId) {
        return sellerRepository.existsById(sellerId);
    }

    @Override
    public Seller findByProduct(int productId) {
        return sellerRepository.findByProduct(productId);
    }

    @Override
    public List<Seller> loadPendingApprovals() {
        List<Seller> sellerList = sellerRepository.findAll();
        List<Seller> sellerListPendingApproval = new ArrayList<>();

        for (Seller seller : sellerList) {
            if (seller.getUser().getAcctStatus() == 4) {
                sellerListPendingApproval.add(seller);
            }
        }

        return sellerListPendingApproval;
    }

    @Override
    public void approveSellerAccount(int sellerId) {
        Seller seller = findById(sellerId);
        seller.getUser().setAcctStatus(1);

        save(seller);
    }

    @Override
    public void rejectSellerAccount(int sellerId) {
        Seller seller = findById(sellerId);
        seller.getUser().setAcctStatus(5);

        save(seller);
    }

    @Override
    public void changeSellerAccountStatus(int sellerId, int newStatus) {
        Seller seller = findById(sellerId);
        seller.getUser().setAcctStatus(newStatus);

        save(seller);
    }

    @Override
    public Seller findByUserId(int userId) {
        return sellerRepository.findByUserId(userId);
    }

    @Override
    public String saveFile(MultipartFile photoFile) throws NullPointerException, IOException {
        String filename = photoFile.getOriginalFilename();
        filename = filename.replaceAll("[^.a-zA-Z0-9]", " ");
        filename = filename.replaceAll(" ", "").trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyyHHmmssSSSnnnnnnnnn");
        LocalDateTime ldt = LocalDateTime.now();
        String ldtString = ldt.format(formatter);
        
        filename = ldtString + filename;

        // Get the filename and build the local file path (be sure that the 
        // application have write permissions on such directory)
        String directory = "C:\\Users\\Executor\\Desktop\\public_html\\dev10-software-guild-files\\Capstone\\Website\\imaginapainting\\src\\main\\resources\\static\\uploaded-photos";
        String filepath = Paths.get(directory, filename).toString();

        // Save the file locally
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
        stream.write(photoFile.getBytes());
        stream.close();

        return filename;

    }

}
