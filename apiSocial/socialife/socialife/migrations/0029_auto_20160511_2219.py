# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0028_auto_20160511_2215'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='estaapuntadoa',
            name='plan',
        ),
        migrations.RemoveField(
            model_name='estaapuntadoa',
            name='usuario',
        ),
        migrations.DeleteModel(
            name='EstaApuntadoA',
        ),
    ]
