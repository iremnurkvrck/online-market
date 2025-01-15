import axios from "axios";

// Sepete ürün ekleme işlevi
export const addToCart = async (cartData) => {
  try {
    const username = localStorage.getItem("username"); // Kullanıcı adını localStorage'dan alıyoruz
    if (!username) {
      throw new Error("Kullanıcı adı bulunamadı. Lütfen giriş yapın.");
    }

    // Kullanıcı adı ve ürün verilerini birleştiriyoruz
    const dataToSend = {
      ...cartData,
      username: username, // Kullanıcı adını da ekliyoruz
    };

    // API'ye gönderilecek veri
    const response = await axios.post(
      `http://localhost:8080/api/v1/auth/add`, // URL'ye kullanıcı adını ekliyoruz
      dataToSend
    );

    return { success: true, data: response.data };
  } catch (error) {
    console.error(
      "Sepete eklerken bir hata oluştu:",
      error.response ? error.response.data : error.message
    );
    return {
      success: false,
      error: error.response ? error.response.data : error.message,
    };
  }
};
