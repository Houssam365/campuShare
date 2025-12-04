package com.campusshare.strategy;

import com.campusshare.model.annonce.Annonce;
import java.util.List;

public interface ITriStrategy {
    String getNom();
    List<Annonce> trier(List<Annonce> annonces);
}