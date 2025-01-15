import React, { useState } from "react";

const Contact = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    message: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    formValidate();
    // Form gönderme işlemi burada yapılabilir
    console.log(formData);
  };

  const formValidate = () => {
    // Form doğrulama işlemleri
    if (
      !formData.name ||
      !formData.email ||
      !formData.phone ||
      !formData.message
    ) {
      alert("Lütfen tüm alanları doldurun.");
    } else {
      alert("Form başarıyla gönderildi.");
    }
  };

  return (
    <section className="contact" id="contact">
      <h2 className="heading">İLETİŞİM</h2>
      <div className="row">
        <form
          className="contact-form"
          onSubmit={handleSubmit}
          name="contactForm"
        >
          <label htmlFor="name">İsim:</label>
          <input
            type="text"
            placeholder="İsim"
            className="box"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
          />

          <label htmlFor="email">E-mail:</label>
          <input
            type="email"
            placeholder="E-mail"
            className="box"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />

          <label htmlFor="phone">Telefon:</label>
          <input
            type="text"
            placeholder="Telefon"
            className="box"
            id="phone"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
          />

          <label htmlFor="message">Mesaj:</label>
          <textarea
            name="message"
            className="box"
            placeholder="Mesaj"
            cols="30"
            rows="10"
            value={formData.message}
            onChange={handleChange}
          ></textarea>

          <input type="submit" value="Mesaj Gönder" className="btn" />
        </form>
      </div>
    </section>
  );
};

export default Contact;
