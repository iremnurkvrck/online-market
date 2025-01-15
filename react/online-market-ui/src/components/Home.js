import React, { useState } from "react";
import { Link } from "react-router-dom"; // Link import ediyoruz
import "./Home.css";
import CategoryCard from "./CategoryCard";
import fruitsImg from "../assets/fruits.jpg";
import meatImg from "../assets/meat.jpg";
import vegetablesImg from "../assets/vegetables.jpg";
import fishImg from "../assets/fish.jpg";
import temizlikImg from "../assets/temizlik-deterjan.jpg";

const Home = () => {
  return (
    <div className="home-bg">
      <section className="home">
        <div className="content">
          <span>Taze, Kaliteli ve Uygun Fiyatlı Ürünler...</span>
          <h3>Her Şey Bir Tık Uzağınızda!</h3>
          <p>
            Taze ürünler, hızlı teslimat ve geniş ürün yelpazemizle
            hizmetinizdeyiz.
          </p>
          <Link to="/about" className="btn">
            Daha Fazla Bilgi
          </Link>
        </div>
      </section>

      <section className="home-category">
        <h1 className="title">Kategoriler</h1>
        <div className="box-container">
          {/* Kategorilere tıklanabilir Link ekliyoruz */}
          <Link to="/searchCategory/Meyve">
            <CategoryCard
              title="Meyve"
              image={fruitsImg}
              description="Lezzetli ve vitamin dolu meyvelerimiz, doğal tatlarıyla sağlıklı bir atıştırmalık seçeneğidir."
            />
          </Link>

          <Link to="/searchCategory/Et">
            <CategoryCard
              title="Et"
              image={meatImg}
              description="Marketimizdeki etler taze, lezzetli ve yüksek protein kaynağıdır, sağlıklı beslenmenizi destekler."
            />
          </Link>

          <Link to="/searchCategory/Sebze">
            <CategoryCard
              title="Sebze"
              image={vegetablesImg}
              description="Taze ve doğal sebzelerimiz, günlük vitamin ve mineral ihtiyacınızı karşılar, sağlıklı beslenmenin temelini oluşturur."
            />
          </Link>

          <Link to="/searchCategory/Balık">
            <CategoryCard
              title="Balık"
              image={fishImg}
              description="Taze ve besleyici balık ürünlerimiz, zengin omega-3 içeriğiyle sağlıklı bir seçenek sunar."
            />
          </Link>

          <Link to="/searchCategory/Cleaning">
            <CategoryCard
              title="Temizlik & Deterjan"
              image={temizlikImg}
              description="Etkili temizlik için güvenle kullanabileceğiniz temizlik ürünlerimiz, hijyenik bir ortam sağlar."
            />
          </Link>
        </div>
      </section>
    </div>
  );
};

export default Home;
