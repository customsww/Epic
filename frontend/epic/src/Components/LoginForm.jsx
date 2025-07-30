import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { IonIcon } from "@ionic/react";
import { mail, lockClosed, person, close } from "ionicons/icons";
import { AuthService } from "../Services/AuthService"; // Đảm bảo file export đúng hàm

export default function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const [isRegister, setIsRegister] = useState(false);
  const [showForm, setShowForm] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    if (!email || !password) {
      setError("Vui lòng nhập đầy đủ email và mật khẩu.");
      return;
    }
    setLoading(true);
    try {
      const dataologin = await AuthService({ email, password });
      if (dataologin.status === 200) {
        navigate("/admin");
      } else {
        setError(dataologin.message || "Lỗi đăng nhập.");
      }
    } catch (err) {
      setError(err.message || "Đăng nhập thất bại.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      className="min-h-screen flex flex-col items-center justify-center bg-cover bg-center"
      style={{
        backgroundImage:
          "url('https://images.unsplash.com/photo-1716654195483-de7a5ad4d23c?q=80&w=1932&auto=format&fit=crop')",
      }}
    >
      <header className="fixed top-0 left-0 w-full px-10 py-5 flex justify-between items-center z-50">
        <h2 className="text-2xl text-white font-semibold select-none">Andev Web</h2>
        <nav>
          <button
            onClick={() => setShowForm(true)}
            className="w-32 h-12 border-2 border-white text-white font-medium rounded-md transition hover:bg-white hover:text-[#162938]"
          >
            Iniciar sesión
          </button>
        </nav>
      </header>

      {showForm && (
        <div className={`relative w-96 bg-white/10 border border-white/30 rounded-xl backdrop-blur-md shadow-lg p-10 transition-transform duration-300 ${isRegister ? 'h-[520px]' : 'h-[470px]'}`}>
          <span
            className="absolute top-0 right-0 w-11 h-11 bg-[#162938] text-white text-2xl flex items-center justify-center rounded-bl-xl cursor-pointer"
            onClick={() => setShowForm(false)}
          >
            <IonIcon icon={close} />
          </span>

          {isRegister ? (
            <div>
              <h2 className="text-2xl text-white text-center mb-5">Registro</h2>
              <form onSubmit={(e) => e.preventDefault()}>
                <InputBox icon={person} label="Nombre de usuario" type="text" />
                <InputBox icon={mail} label="Correo electrónico" type="email" />
                <InputBox icon={lockClosed} label="Contraseña" type="password" />
                <div className="text-white text-sm mb-5">
                  <label>
                    <input type="checkbox" className="mr-2" required />
                    Acepto los términos y condiciones
                  </label>
                </div>
                <button type="submit" className="w-full h-11 bg-white text-[#162938] rounded-md font-medium">
                  Registrarse
                </button>
                <p className="text-white text-sm text-center mt-6">
                  ¿Ya tienes una cuenta?{" "}
                  <span
                    onClick={() => setIsRegister(false)}
                    className="font-semibold underline cursor-pointer"
                  >
                    Iniciar sesión
                  </span>
                </p>
              </form>
            </div>
          ) : (
            <div>
              <h2 className="text-2xl text-white text-center mb-5">Iniciar sesión</h2>
              <form onSubmit={handleSubmit}>
                <InputBox
                  icon={mail}
                  label="Correo electrónico"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
                <InputBox
                  icon={lockClosed}
                  label="Contraseña"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <div className="flex justify-between items-center text-white text-sm mb-5">
                  <label>
                    <input type="checkbox" className="mr-2" required /> Recordarme
                  </label>
                  <a href="#" className="hover:underline">
                    ¿Olvidaste la contraseña?
                  </a>
                </div>
                {error && (
                  <div className="text-red-400 text-sm text-center mb-3">{error}</div>
                )}
                <button
                  type="submit"
                  className="w-full h-11 bg-white text-[#162938] rounded-md font-medium"
                  disabled={loading}
                >
                  {loading ? "Đang đăng nhập..." : "Iniciar sesión"}
                </button>
                <p className="text-white text-sm text-center mt-6">
                  ¿No tienes una cuenta?{" "}
                  <span
                    onClick={() => setIsRegister(true)}
                    className="font-semibold underline cursor-pointer"
                  >
                    Registrarse
                  </span>
                </p>
              </form>
            </div>
          )}
        </div>
      )}
    </div>
  );
}

function InputBox({ icon, label, type, value, onChange }) {
  return (
    <div className="relative w-full h-12 border-b-2 border-white mb-6">
      <input
        type={type}
        required
        value={value}
        onChange={onChange}
        className="w-full h-full bg-transparent text-white font-medium pl-2 pr-10 focus:outline-none peer"
      />
      <label className="absolute left-2 top-1/2 transform -translate-y-1/2 text-white text-sm transition-all peer-focus:top-[-5px] peer-valid:top-[-5px]">
        {label}
      </label>
      <span className="absolute right-2 top-1/2 transform -translate-y-1/2 text-white">
        <IonIcon icon={icon} />
      </span>
    </div>
  );
}
