import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./components/Home";
import About from "./components/About"; // About bileşenini import et
import LoginPage from "./components/LoginPage"; // LoginPage bileşenini import et
import Register from "./components/RegisterPage"; // Register bileşenini import et
import ProductList from "./components/ProductList"; // ProductList bileşenini import et
import WishList from "./components/WishList"; // ProductList bileşenini import et
import SearchCategory from "./components/SearchCategory";
import ProductView from "./components/ProductView";
import ShoppingCart from "./components/ShowCart";
import Profile from "./components/Profile";
import FavoritesList from "./components/FavoriteList";
import ContactForm from "./components/ContactForm";
import Orders from "./components/Orders";
import OrdersList from "./components/OrdersList";

function App() {
  return (
    <Router>
      <div>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<Register />} />
          <Route path="/products" element={<ProductList />} />
          <Route path="/wishlist" element={<WishList />} />
          <Route path="/productView" element={<ProductView />} />
          <Route path="/shoppingCart" element={<ShoppingCart />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/favoriteList" element={<FavoritesList />} />
          <Route path="/contact" element={<ContactForm />} />
          <Route path="/orders" element={<Orders />} />
          <Route path="/ordersList" element={<OrdersList />} />

          <Route
            path="/searchCategory/:category"
            element={<SearchCategory />}
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
