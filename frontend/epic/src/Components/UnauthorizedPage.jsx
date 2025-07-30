import './css/403.css'
export default function UnauthorizedPage() {
  return (
    <div className="bg-[#000121] font-roboto min-h-screen">
      <div className="maincontainer mx-auto relative scale-[0.8] -top-[50px] grid w-[800px] h-[600px] bg-center bg-no-repeat bg-cover"
        style={{ backgroundImage: "url('https://aimieclouse.com/Media/Portfolio/Error403Forbidden/HauntedHouseBackground.png')" }}
      >
        <h1>hi</h1>
        {[...Array(3)].map((_, i) => (
          <div className={`bat bat-${i + 1}`} key={i}>
            <img
              className="wing leftwing"
              src="https://aimieclouse.com/Media/Portfolio/Error403Forbidden/bat-wing.png"
              alt="left wing"
            />
            <img
              className="body"
              src="https://aimieclouse.com/Media/Portfolio/Error403Forbidden/bat-body.png"
              alt="bat body"
            />
            <img
              className="wing rightwing"
              src="https://aimieclouse.com/Media/Portfolio/Error403Forbidden/bat-wing.png"
              alt="right wing"
            />
          </div>
        ))}

        <img
          className="foregroundimg relative top-[-230px] z-[5] w-full"
          src="https://aimieclouse.com/Media/Portfolio/Error403Forbidden/HauntedHouseForeground.png"
          alt="haunted house"
        />
      </div>

      <h1 className="errorcode relative -top-[200px] text-white text-center text-[6em] tracking-wider font-[Creepster,cursive]">
        ERROR 403
      </h1>
      <div className="errortext relative -top-[260px] text-[#FBD130] text-center uppercase text-[1.8em]">
        This area is forbidden. Turn back now!
      </div>
    </div>
  );
}
