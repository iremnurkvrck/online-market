import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom"; // useParams'i import ediyoruz
import ProductView from "./ProductView"; // ProductView bileşenini import ettik
import "./Product.css"; // CSS dosyasını import edin

const SearchCategory = () => {
  const { category } = useParams(); // URL'deki category parametresini alıyoruz
  const [products, setProducts] = useState([]);
  const [quantities, setQuantities] = useState({}); // Her ürün için miktarları tutacak state
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(""); // Hata mesajı için state

  // Veriyi çekme fonksiyonu
  const fetchProductsByCategory = async (category) => {
    setLoading(true);
    setError(""); // Önceki hatayı temizle

    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/auth/category/${encodeURIComponent(
          category
        )}`
      );
      if (!response.ok) {
        throw new Error("Veri alınırken hata oluştu.");
      }
      const data = await response.json();
      setProducts(data); // Veriyi state'e kaydediyoruz
    } catch (error) {
      setError(`${category} kategorisi verisi alınırken hata oluştu.`);
    } finally {
      setLoading(false);
    }
  };

  // Kategori değiştiğinde veri çekmek için useEffect kullanıyoruz
  useEffect(() => {
    fetchProductsByCategory(category); // Kategoriyi aldık ve veriyi çektik
  }, [category]);

  // Miktar değişikliklerini işleme fonksiyonu
  const handleQuantityChange = (productId, event) => {
    setQuantities({
      ...quantities,
      [productId]: event.target.value,
    });
  };

  return (
    <div className="product-list">
      <h1 className="title">{category} Kategorisi</h1>

      {/* Loading durumu */}
      {loading && <p>Yükleniyor...</p>}

      {/* Hata durumu */}
      {error && <p>{error}</p>}

      {/* ProductView bileşenine gerekli props'ları gönderiyoruz */}
      <ProductView
        products={products}
        quantities={quantities}
        handleQuantityChange={handleQuantityChange}
      />
    </div>
  );
};

export default SearchCategory;
