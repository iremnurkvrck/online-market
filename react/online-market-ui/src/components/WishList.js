import React from "react";
import { useLocation, Link } from "react-router-dom"; // useLocation ile gelen veriyi alıyoruz
import ProductView from "./ProductView"; // ProductView bileşenini import ediyoruz
import defaultImage from "../assets/default-image.png"; // Varsayılan resim
import "./Product.css";

const WishList = () => {
  // useLocation hook'u ile yönlendirme sonucu ürün bilgilerini alıyoruz
  const location = useLocation();
  const { product } = location.state || {}; // ProductList'den gelen 'product' bilgisi

  if (!product) {
    return <p>Ürün bilgileri alınamadı!</p>;
  }

  // Ürün resminin doğru şekilde yüklenebilmesi için resim yolunu kontrol et
  const productImage = product.image
    ? require(`../assets/${product.image}`) // Eğer resim adı varsa, assets klasöründen yükle
    : defaultImage; // Yoksa varsayılan resmi kullan

  // ProductView bileşenini kullanarak gösterim yapıyoruz
  return (
    <div className="wishlist-box-container">
      <h1 className="title">Ürün Detayları</h1>
      <Link
        to={{ pathname: "/productView", state: { product } }}
        className="bi bi-eye"
      ></Link>

      <ProductView
        products={[product]} // ProductView bileşeni tek bir ürünü alacak şekilde güncellendi
        quantities={{}} // Eğer miktar yönetimi yapılacaksa burada geçebilirsiniz
        handleQuantityChange={() => {}} // Boş bir fonksiyon, çünkü burada miktar değişikliği gerekmedi
      />
    </div>
  );
};

export default WishList;
