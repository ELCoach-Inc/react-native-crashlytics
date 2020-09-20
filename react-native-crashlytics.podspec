require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-crashlytics"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-crashlytics
                   DESC
  s.homepage     = "https://github.com/El-Coach/react-native-crashlytics"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "Muhammad Hashim" => "msg@mhasihm6.me" }
  s.platforms    = { :ios => "10.0" }
  s.source       = { :git => "https://github.com/El-Coach/react-native-crashlytics.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency "Firebase/Crashlytics"
  s.dependency "Firebase/Analytics"
  # ...
  # s.dependency "..."
  
end

