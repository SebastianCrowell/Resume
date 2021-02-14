import React from 'react'
import ReactDOM from 'react-dom'

import useModel from './useModel'
import ObjectDetectionVideo from './object-detection-video/cam-obj-detection'

import './index.css'

const App = () => {
  const model = useModel(process.env.PUBLIC_URL + '/model_web')

  return (
    <div class="card container bg-dark">
      <div className="row">
        <div className="fillPage col-8">
          <ObjectDetectionVideo
            model={model}
            // aspectFill: The option to scale the video to fill the size of the view.
            //             Some portion of the video may be clipped to fill the view's
            //             bounds.
            // aspectFit:  The option to scale the video to fit the size of the view
            //             by maintaining the aspect ratio. Any remaining area of the
            //             view's bounds is transparent.
            fit="aspectFill"
          />
        </div>
        <div className ="padded-poem col-4">
          <div class="card">
            <div class="poem card-header">Poem:</div>
            <div class="poem card-body">
            </div>
          </div>
        </div>
        <div className ="padded-button col-1">
          <button class="btn poem btn-danger" type="button">Capture!</button>
        </div>
      </div>
    </div>
  )
}

const rootElement = document.getElementById('root')
ReactDOM.render(<App />, rootElement)
