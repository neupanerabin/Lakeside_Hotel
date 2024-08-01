import React, { createContext, useState, useContext } from "react";
import jwt_decode from 'jwt-decode';

export const AuthContext = createContext({
    user: null,
    handleLogin: (token) => {},
    handleLogout: () => {}
});

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    const handleLogin = (token) => {
        try {
            console.log("Token received:", token); // Add this line
            const decodedUser = jwt_decode(token);
            console.log("Decoded user:", decodedUser); // Debugging line
            localStorage.setItem("userId", decodedUser.sub);
            localStorage.setItem("userRole", decodedUser.role); // Adjust this if the role is different
            localStorage.setItem("token", token);
            setUser(decodedUser);
        } catch (error) {
            console.error("Failed to decode token", error);
        }
    };
    

    const handleLogout = () => {
        localStorage.removeItem("userId");
        localStorage.removeItem("userRole");
        localStorage.removeItem("token");
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, handleLogin, handleLogout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};
