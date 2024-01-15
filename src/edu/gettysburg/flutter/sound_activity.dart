import 'package:flutter/material.dart';

class SoundActivity extends StatefulWidget {
  const SoundActivity({super.key});

  @override
  State<SoundActivity> createState() => _SoundActivityState();
}

class _SoundActivityState extends State<SoundActivity> {
  bool musicChecked = false;
  bool soundChecked = false;

  @override
  void initState() {
    super.initState();
    //TODO: need update from file Maze
    Maze maze = Maze.getInstance();
    
    musicChecked = maze.serviceRunning;
    soundChecked = maze.effectsOn;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Sound Settings'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            CheckboxListTile(
              title: const Text('Background Music'),
              value: musicChecked,
              onChanged: (value) {
                setState(() {
                  musicChecked = value!;
                  if (!musicChecked) {
                    Maze.getInstance().stopMusic();
                    //Toast.makeText(context, "Music turned OFF", Toast.LENGTH_SHORT).show();
                  } else {
                    Maze.getInstance().startMusic();
                    //Toast.makeText(context, "Music turned ON", Toast.LENGTH_SHORT).show();
                  }
                });
              },
            ),
            CheckboxListTile(
              title: const Text('Sound Effects'),
              value: soundChecked,
              onChanged: (value) {
                setState(() {
                  soundChecked = value!;
                  if (!soundChecked) {
                    Maze.getInstance().effectsOn = false;
                    //Toast.makeText(context, "Sound Effects turned OFF", Toast.LENGTH_SHORT).show();
                  } else {
                    Maze.getInstance().effectsOn = true;
                    //Toast.makeText(context, "Sound Effects turned ON", Toast.LENGTH_SHORT).show();
                  }
                });
              },
            ),
          ],
        ),
      ),
    );
  }
}
