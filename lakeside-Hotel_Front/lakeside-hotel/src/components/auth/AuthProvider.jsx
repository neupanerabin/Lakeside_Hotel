import React, { createContext, useState } from 'react';

// Helper function to require the jwt-decode module
const loadJwtDecode = () => {
    const jwtDecode = require("jwt-decode");
    return jwtDecode.default || jwtDecode;
};

export const AuthContext = createContext({
    user: null,
    handleLogin: (token) => {},
    handleLogout: () => {}
});

const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    const handleLogin = (token) => {
        try {
            const jwt_decode = loadJwtDecode();
            const decodedToken = jwt_decode(token);
            localStorage.setItem("userId", decodedToken.sub);
            localStorage.setItem("userRole", decodedToken.role);
            localStorage.setItem("token", token);
            setUser(decodedToken);
        } catch (error) {
            console.error("Failed to decode token", error);
            // Handle token decoding errors here
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

export default AuthProvider;
