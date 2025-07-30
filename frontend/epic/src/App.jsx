  // App.jsx
  import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
  import ProtectedRoute from "./Components/ProtectedRoute";

  // Các trang
  // import AdminPage from "./pages/AdminPage";
  // import HomePage from "./pages/HomePage";
  import LoginPage from "./Components/LoginForm";
  import UnauthorizedPage from "./Components/UnauthorizedPage";

  function App() {
    return (
      <Router>
        <Routes>
          {/* <Route path="/" element={<HomePage />} /> */}

          <Route path="/login" element={<LoginPage />} />


          {/* <Route
            path="/admin"
            element={
              <ProtectedRoute>
                <AdminPage />
              </ProtectedRoute>
            }
          /> */}

          <Route path="/unauthorized" element={<UnauthorizedPage />} />
        </Routes>
      </Router>
    );
  }

  export default App;
