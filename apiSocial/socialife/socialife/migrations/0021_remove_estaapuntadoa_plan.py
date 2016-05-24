# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0020_auto_20160511_2047'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='estaapuntadoa',
            name='plan',
        ),
    ]
