import React from "react";
import about1Img from "../assets/about-1.png";
import about2Img from "../assets/about-2.png";

import "./About.css";

// Box bileşeni
const Box = ({ imgSrc, title, description, buttonText, link }) => {
  return (
    <div className="box">
      <img src={imgSrc} alt={title} />
      <h3>{title}</h3>
      <p>{description}</p>
      <a href={link} className="btn">
        {buttonText}
      </a>
    </div>
  );
};

// About bileşeni
const About = () => {
  return (
    <section className="about">
      <div className="row">
        <Box
          imgSrc={about1Img}
          title="Neden bizi tercih etmelisiniz?"
          description="Çünkü müşteri memnuniyeti bizim için her şeyden önce gelir. Yüksek kaliteli ürünlerimiz, hızlı teslimat seçeneklerimiz ve 7/24 müşteri desteğimiz ile her zaman yanınızdayız. Sizin için değerli olan ne varsa, onu ön planda tutarak size özel çözümler sunuyoruz. Bizimle çalışarak güvenilir, hızlı ve müşteri odaklı bir hizmetin keyfini çıkarabilirsiniz"
          buttonText="İletişim"
          link="/contact"
        />

        <Box
          imgSrc={about2Img}
          title="Müşterilerimize ne gibi çözümler sunuyoruz?"
          description="Biz, yüksek kaliteli ürünler ve hizmetler sunuyoruz. Müşterilerimize hızlı teslimat, güvenli ödeme seçenekleri ve 7/24 müşteri desteği sağlıyoruz. Ayrıca, her ihtimale karşı size özel çözümler ve öneriler sunarak memnuniyetinizi ön planda tutuyoruz. Sizin için en iyi deneyimi yaşatmayı amaçlıyoruz."
          buttonText="Ürünler"
          link="/products"
        />
      </div>
    </section>
  );
};

export default About;
