Pod::Spec.new do |s|
  s.name             = 'flutter_media_session'
  s.version          = '0.0.1'
  s.summary          = 'Flutter Media Session.'
  s.description      = <<-DESC
Send Media Session item to the OS.
                       DESC
  s.homepage         = 'https://aestesis.org'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'aestesis' => 'renan@aestesis.org' }
  s.source           = { :path => '.' }
  s.source_files = 'flutter_media_session/Sources/flutter_media_session/**/*'
  s.dependency 'Flutter'
  s.platform = :ios, '16.0'

  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
  s.swift_version = '5.0'

end
