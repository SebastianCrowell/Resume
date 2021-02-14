import { useEffect } from 'react'

const useCam = (videoRef, onLoaded) => {
  useEffect(() => {
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      navigator.mediaDevices
        .getUserMedia({
          audio: false,
          video: {
            facingMode: 'face',
            width: { ideal: 1920 },
            height: { ideal: 1080 },
          },
        })
        .then((stream) => {
          videoRef.current.srcObject = stream
          videoRef.current.onloadedmetadata = () => {
            onLoaded()
          }
        })
    }
  }, [onLoaded, videoRef])
}

export default useCam
